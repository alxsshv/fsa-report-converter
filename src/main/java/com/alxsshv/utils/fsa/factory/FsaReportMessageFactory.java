package com.alxsshv.utils.fsa.factory;

import com.alxsshv.model.VerificationRecord;
import com.alxsshv.utils.fsa.entity.FsaReportMessage;
import com.alxsshv.utils.fsa.entity.MeasurementInstrument;
import com.alxsshv.utils.fsa.entity.MeasuringInstrumentData;

import java.util.List;

public class FsaReportMessageFactory {
    public static FsaReportMessage createMessage(List<VerificationRecord> records){
        MeasuringInstrumentData data = new MeasuringInstrumentData();
        for (VerificationRecord record: records) {
            MeasurementInstrument instrument = MeasurementInstrumentFactory.createInstrument(record);
            data.add(instrument);
        }
        return new FsaReportMessage(data,2);
    }
}
