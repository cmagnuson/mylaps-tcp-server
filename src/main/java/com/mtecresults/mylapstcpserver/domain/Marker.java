package com.mtecresults.mylapstcpserver.domain;

import lombok.Data;

import java.util.Arrays;
import java.util.Collection;

@Data
public class Marker {
    final long timeMillis;
    final String label;
    final String locationName;
    final MarkerType type;

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
