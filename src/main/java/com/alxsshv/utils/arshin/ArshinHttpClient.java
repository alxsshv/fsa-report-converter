package com.alxsshv.utils.arshin;

import com.alxsshv.exceptions.ArshinResponseException;
import com.alxsshv.utils.arshin.entities.Response;
import com.alxsshv.utils.arshin.entities.mit.MitItem;
import com.alxsshv.utils.arshin.entities.mit.MitResponse;
import com.alxsshv.utils.arshin.entities.vri.VriItem;
import com.alxsshv.utils.arshin.entities.vri.VriResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;


@Getter
@Setter
@Slf4j
public class ArshinHttpClient {
    private static int failCountLimit = 5;

    public static VriItem getVerificationItemIfOnlyMatches(String verificationRequest, int failCount) throws ArshinResponseException, HttpServerErrorException {
        try {
            RestClient restClient = RestClient.create();
            VriResponse response = restClient.get().uri(verificationRequest).retrieve().body(VriResponse.class);
            if (response != null && response.getResult().getCount() == 1) {
                return response.getResult().getItems().get(0);
            }
            throw new ArshinResponseException(getErrorMessage(response));
        } catch (ResourceAccessException ex) {
            log.error(ex.getMessage());
            throw new ArshinResponseException("Ошибка соединения с сервером ФГИС \"Аршин\". Проверьте интернет соединение и доступность серверов ФГИС \"Аршин\"");
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            throw new ArshinResponseException("Ошибка взаимодейтсвия ПО с сервером ФГИС \"Аршин\". Пожалуйста повторите попытку");
        } catch (HttpServerErrorException ex) {
            if (failCount < failCountLimit) {
                failCount++;
                return getVerificationItemIfOnlyMatches(verificationRequest, failCount);
            } else {
                throw new HttpServerErrorException(ex.getStatusCode(), ex.getMessage());
            }
        }
    }



    public static MitItem getMiTypeItemIfOnlyMatches(String miTypeRequest, int failCount) throws ArshinResponseException {
        try {
            RestClient restClient = RestClient.create();
            MitResponse response = restClient.get().uri(miTypeRequest).retrieve().body(MitResponse.class);
            if (response != null && response.getResult().getCount() == 1) {
                return response.getResult().getItems().get(0);
             }
            throw new ArshinResponseException(getErrorMessage(response));
        } catch (ResourceAccessException ex){
            throw new ArshinResponseException("Ошибка соединения с сервером ФГИС\"Аршин\". Проверьте интернет соединение и доступность серверов ФГИС \"Аршин\"");
        } catch (HttpServerErrorException ex) {
            if (failCount < failCountLimit) {
                failCount++;
                return getMiTypeItemIfOnlyMatches(miTypeRequest, failCount);
            } else {
                throw new HttpServerErrorException(ex.getStatusCode(), ex.getMessage());
            }
        }
    }

    private static String getErrorMessage(Response response){
        if (response == null){
            return "Сервер ФГИС \"Аршин\" не отвечает";
        }
        if (response.getResult().getCount() == 0){
            return "По указанному запросу записей в ФГИС \"Аршин\" не найдено";
        }
        if (response.getResult().getCount() > 1){
            return "Количество полученных записей из ФГИС \"Аршин\" = " + response.getResult().getCount()
                    + " что не позволяет однозначно определить какая запись соответствует запросу";
        }
        return "Ошибка чтения данных полученных от ФГИС \"Аршин\"";
    }



}
