package com.mtecresults.mylapstcpserver.domain;

import com.mtecresults.mylapstcpserver.controller.ParseUtils;
import com.mtecresults.mylapstcpserver.controller.TCPMinaHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;

@Data
public class Marker {

    private static final Logger LOG = LoggerFactory.getLogger(Marker.class);

    final long timeMillis;
    final String label;
    final String locationName;
    final MarkerType type;

    public static List<Marker> parseMarkers(String location, String[] parts) {
        List<Marker> markers = new ArrayList<>();
        for(int i=2; i<parts.length-2; i++){
            try {
                HashMap<String, String> partsMap = ParseUtils.partToMap(parts[i]);
                String timeStr = partsMap.get("t");
                long time = ParseUtils.parseDateTimeString(timeStr);
                String label = partsMap.get("n");
                String markerTypeStr = partsMap.get("mt");
                Marker.MarkerType markerType = Marker.MarkerType.parseProtocolString(markerTypeStr);

                Marker marker = new Marker(time, label, location, markerType);
                markers.add(marker);
            }
            catch(ParseException | NumberFormatException e){
                LOG.error("Parse error handling markers: "+parts[i], e);
            }
        }
        return markers;
    }

    public enum MarkerType {
        GUNSHOT(true, Arrays.asList("gun")),
        EXT_GUN(true, Arrays.asList("ext-1", "ext-2", "ext-gun")),
        MARKER(false, Arrays.asList("mark")),
        LOCATION(false, Arrays.asList("loc")),
        UNKNOWN(false, Arrays.asList("")),
        ;

        final boolean isGunshot;
        final Collection<String> protocolStrings;

        MarkerType(final boolean isGunshot, Collection<String> protocolStrings){
            this.isGunshot = isGunshot;
            this.protocolStrings = protocolStrings;
        }

        public static MarkerType parseProtocolString(String protoString){
            for(MarkerType mt: values()){
                if(mt.protocolStrings.contains(protoString)){
                    return mt;
                }
            }
            return UNKNOWN;
        }
    }
}
