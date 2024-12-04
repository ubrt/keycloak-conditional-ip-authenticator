# Keycloak Conditional IP Authenticator

[Latest Release](https://github.com/ubrt/keycloak-conditional-ip-authenticator/releases/latest)

[![Build and Release](https://github.com/ubrt/keycloak-conditional-ip-authenticator/actions/workflows/build.yml/badge.svg)](https://github.com/ubrt/keycloak-conditional-ip-authenticator/actions/workflows/build.yml)

## Installation

1. Download the release compatible with your Keycloak version from the [Releases page](https://github.com/ubrt/keycloak-conditional-ip-authenticator/releases).
2. Place the JAR file into the `providers` directory of your Keycloak installation.

## Usage

1. Add a sub-flow to your authentication flow and set it to **Alternative**. *(See Screen 1)*
2. Within the sub-flow, add another sub-flow and set it to **Conditional**.
3. Add a step of type **Condition** to the conditional sub-flow: **"Condition - IP Match"**.
4. Configure the **Condition - IP Match**:
   - Add the IPs to match in CIDR format, with one IP per line (e.g., `192.168.0.1/24`). *(See Screen 2)*.
   - Enable **Invert Logic** if you want the condition to pass when no IP matches.
5. Add the steps you want to execute when the condition passes.

### Screen 1

<img width="1353" alt="image" src="https://github.com/user-attachments/assets/2fed2767-77cf-48f4-94c2-68a8ee251a35">

### Screen 2

<img width="404" alt="image" src="https://github.com/user-attachments/assets/bb004617-11e6-4904-afaf-5c0dbe688926">
