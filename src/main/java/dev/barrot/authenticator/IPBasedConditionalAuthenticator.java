package dev.barrot.authenticator;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Arrays;


public class IPBasedConditionalAuthenticator implements ConditionalAuthenticator {

    private static final Logger logger = Logger.getLogger(IPBasedConditionalAuthenticator.class);
    static final IPBasedConditionalAuthenticator SINGLETON = new IPBasedConditionalAuthenticator();


    @Override
    public boolean matchCondition(AuthenticationFlowContext context) {
        AuthenticatorConfigModel config = context.getAuthenticatorConfig();

        String ipRanges = config != null ? config.getConfig().get("ip-range") : null;
        boolean invert = config != null && Boolean.parseBoolean(config.getConfig().get("invert"));

        if (ipRanges == null || ipRanges.isEmpty()) {
            return invert;
        }

        var ipAddresses = Arrays.asList(ipRanges.split("##"));
        String clientIp = context.getConnection().getRemoteAddr();

        if (IPMatcher.isIpInAnyCidr(clientIp, ipAddresses)) {
            return !invert;
        }

        return invert;
    }

    @Override
    public void action(AuthenticationFlowContext context) {

    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }

    @Override
    public void close() {

    }
}
