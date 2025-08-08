package com.mtecresults.mylapstcpserver.domain;

import com.mtecresults.mylapstcpserver.controller.ParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;

public record Marker(long timeMillis, String label, String locationName, MarkerType type) {

    private static final Logger LOG = LoggerFactory.getLogger(Marker.class);

    public static List<Marker> parseMarkers(String location, String[] parts) {
        List<Marker> markers = new ArrayList<>();
        for (int i = 2; i < parts.length - 2; i++) {
            try {
                HashMap<String, String> partsMap = ParseUtils.partToMap(parts[i]);
                String timeStr = partsMap.get("t");
                long time = ParseUtils.parseDateTimeString(timeStr);
                String label = partsMap.get("n");
                String markerTypeStr = partsMap.get("mt");
                MarkerType markerType = MarkerType.parseProtocolString(markerTypeStr);

                Marker marker = new Marker(time, label, location, markerType);
                markers.add(marker);
            } catch (ParseException | NumberFormatException e) {
                LOG.error("Parse error handling markers: " + parts[i], e);
            }
        }
        return markers;
    }

    public enum MarkerType {
        GUNSHOT(true, List.of("gun")),
        EXT_GUN(true, Arrays.asList("ext-1", "ext-2", "ext-gun")),
        MARKER(false, List.of("mark")),
        LOCATION(false, List.of("loc")),
        UNKNOWN(false, List.of("")),
        ;

        final boolean isGunshot;
        final Collection<String> protocolStrings;

        MarkerType(final boolean isGunshot, Collection<String> protocolStrings) {
            this.isGunshot = isGunshot;
            this.protocolStrings = protocolStrings;
        }

        public static MarkerType parseProtocolString(String protoString) {
            for (MarkerType mt : values()) {
                if (mt.protocolStrings.contains(protoString)) {
                    return mt;
                }
            }
            return UNKNOWN;
        }
    }
}
