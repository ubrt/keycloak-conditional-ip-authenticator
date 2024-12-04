package dev.barrot.authenticator;

import com.google.auto.service.AutoService;
import org.keycloak.Config;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

@AutoService(AuthenticatorFactory.class)
public class IPBasedConditionalAuthenticatorFactory implements ConditionalAuthenticatorFactory {
    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED, AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public String getId() {
        return "conditional-ip-authenticator";
    }

    @Override
    public String getDisplayType() {
        return "Condition - ip match";
    }

    @Override
    public ConditionalAuthenticator getSingleton() {
        return IPBasedConditionalAuthenticator.SINGLETON;
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        List<ProviderConfigProperty> properties = new ArrayList<>();

        ProviderConfigProperty invert = new ProviderConfigProperty();
        invert.setName("invert");
        invert.setLabel("Invert Logic");
        invert.setDefaultValue("false");
        invert.setType(ProviderConfigProperty.BOOLEAN_TYPE);
        invert.setHelpText("If invert logic is enabled the flow is executed when no ip address matches.");
        properties.add(invert);

        ProviderConfigProperty ipRangeProperty = new ProviderConfigProperty();
        ipRangeProperty.setName("ip-range");
        ipRangeProperty.setLabel("IP Ranges");
        ipRangeProperty.setType(ProviderConfigProperty.MULTIVALUED_STRING_TYPE);
        ipRangeProperty.setHelpText("IP ranges in CIDR notation (e.g. ::1/128 or 192.168.0.1/24).");
        properties.add(ipRangeProperty);

        return properties;
    }

    @Override
    public String getHelpText() {
        return "Flow is executed only if the client ip address matches any defined ip cidrs. The logic can also be inverted (execute if no match).";
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void close() {
    }
}
