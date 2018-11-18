package com.mtecresults.mylapstcpserver.controller;

import com.mtecresults.mylapstcpserver.domain.Passing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ParseUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ParseUtils.class);
    private static final ThreadLocal<DateFormat> dateFormat = new ThreadLocal(){
        @Override
        public Object get() {
            return new SimpleDateFormat("HH:mm:ss.SSS");
        }
    };

    public static HashMap<String, String> partToMap(String part){
        String[] passingParts = part.split("\\|");
        HashMap<String, String> partsMap = new HashMap<>();
        for(String passingPart: passingParts){
            String[] kv = passingPart.split("=");
            if(kv.length != 2){
                LOG.warn("Invalid V2 key value pair: "+part);
                continue;
            }
            partsMap.put(kv[0], kv[1]);
        }
        return partsMap;
    }

    public static long parseDateTimeString(String dateTimeString) throws ParseException, NumberFormatException {
        String[] parts = dateTimeString.split(" ");
        if(parts.length != 2){
            throw new NumberFormatException("Invalid date time received: "+dateTimeString);
        }
        return parseTimeString(parts[1]);
    }

    public static long parseTimeString(String timeString) throws ParseException {
        return dateFormat.get().parse(timeString).getTime();
    }
}
