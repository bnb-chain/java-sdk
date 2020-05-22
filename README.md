# Binance Chain Java SDK

The Binance Chain Java SDK works as a lightweight Java library for interacting with the [Binance Chain](https://binance-chain.github.io/api-reference/dex-api/paths.html). It provides a complete API coverage, and supports synchronous and asynchronous requests.  It includes the following core components:

* **[crypto](https://github.com/binance-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/encoding/Crypto.java)** - core cryptographic functions.
* **[amino encoding](https://github.com/binance-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/encoding)** - [amino](https://github.com/binance-chain/docs-site/blob/master/docs/encoding.md) (protobuf-like) encoding and decoding of transactions.
* **[client](https://github.com/binance-chain/java-sdk/tree/master/src/main/java/com/binance/dex/api/client/impl)** - implementations of API rest client, supporting synchronous and asynchronous access to Binance Chain's REST APIs.
* **[wallet](https://github.com/binance-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/Wallet.java)** - management of accounts, including seed and encrypted mnemonic generation.

## Disclaimer
**This branch is under active development, all subject to potential future change without notification and not ready for production use. The code and security audit have not been fully completed and not ready for any bug bounty.**

# Installation


1. Install library into your Maven's local repository by running `mvn install`
2. Add the following Maven dependency to your project's `pom.xml`:
```
<dependency>
    <groupId>com.binance.dex.api</groupId>
    <artifactId>binance-dex-api-client</artifactId>
    <version>1.1.0</version>
</dependency>
```
# Protobuf

The protobuf-maven-plugin is used in this SDK. It is a plugin that integrates protocol buffers compiler (protoc) into Maven lifecycle. The Plugin generates Java source files from .proto (protocol buffer definition) files for this project by running `mvn compile`. 

These Java source files will be packaged into the final artifact and referenced as imports from the dependent projects or modules by running `mvn clean package`.

More details please refer to https://github.com/xolstice/protobuf-maven-plugin

 

# API

For examples, please check the [wiki](https://github.com/binance-chain/java-sdk/wiki).

# Testing

All new code changes should be covered with unit tests. You can see the existing test cases here: https://github.com/binance-chain/java-sdk/tree/master/src/test/java/com/binance/dex/api/client/encoding 


# Contributing

Contributions to the Binance Chain Java SDK are welcome. Please ensure that you have tested the changes with a local client and have added unit test coverage for your code.

