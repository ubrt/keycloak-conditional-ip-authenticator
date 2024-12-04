package dev.barrot.authenticator;

import org.jboss.logging.Logger;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.List;

public class IPMatcher {

    private static final Logger logger = Logger.getLogger(IPMatcher.class);

    public static boolean isIpInAnyCidr(String ip, List<String> cidrList) {
        return cidrList.stream().anyMatch(cidr -> isIpInCidr(ip, cidr.trim()));
    }

    private static boolean isIpInCidr(String ip, String cidr) {
        try {
            String[] parts = cidr.split("/");
            String baseAddress = parts[0];
            int networkPrefix = Integer.parseInt(parts[1]);

            BigInteger ipBigInt = ipToBigInt(ip);
            BigInteger baseAddressBigInt = ipToBigInt(baseAddress);
            BigInteger mask = BigInteger.valueOf(-1).shiftLeft(32 - networkPrefix);

            return ipBigInt.and(mask).equals(baseAddressBigInt.and(mask));
        } catch (Exception e) {
            logger.error("Error checking IP " + ip + " against CIDR " + cidr + ": " + e.getMessage() + " Please provide a valid cidr format.");
            return false;
        }
    }

    private static BigInteger ipToBigInt(String ip) throws Exception {
        InetAddress inetAddress = InetAddress.getByName(ip);
        byte[] addressBytes = inetAddress.getAddress();
        return new BigInteger(1, addressBytes);
    }
}
