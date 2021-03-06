package com.mtecresults.mylapstcpserver.domain;

import com.mtecresults.mylapstcpserver.controller.ParseUtils;
import com.mtecresults.mylapstcpserver.controller.TCPMinaHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Passing {

    private static final Logger LOG = LoggerFactory.getLogger(Passing.class);

    final long timeMillis;
    final String chipcode;
    final String locationName;

    public static List<Passing> parseTimes(String location, String[] parts) {
        List<Passing> passings = new ArrayList<>();
        for(int i=2; i<parts.length-2; i++){
            try {
                HashMap<String, String> partsMap = ParseUtils.partToMap(parts[i]);
                String chip = partsMap.get("c");
                String timeStr = partsMap.get("t");
                long time = ParseUtils.parseTimeString(timeStr);

                Passing passing = new Passing(time, chip, location);
                passings.add(passing);
            }
            catch(ParseException | NumberFormatException e){
                LOG.error("Parse error handling times: "+parts[i], e);
            }
        }
        return passings;
    }
}
