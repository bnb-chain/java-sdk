# BNB Beacon Chain Java SDK

The BNB Beacon Chain Java SDK works as a lightweight Java library for interacting with the [BNB Beacon Chain](https://docs.bnbchain.org/docs/beaconchain/develop/api-reference/dex-api/paths). It provides a complete API coverage, and supports synchronous and asynchronous requests.  It includes the following core components:

* **[crypto](https://github.com/bnb-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/encoding/Crypto.java)** - core cryptographic functions.
* **[amino encoding](https://github.com/bnb-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/encoding)** - [amino](https://docs.bnbchain.org/docs/beaconchain/learn/encoding/encoding/#amino) (protobuf-like) encoding and decoding of transactions.
* **[client](https://github.com/bnb-chain/java-sdk/tree/master/src/main/java/com/binance/dex/api/client/impl)** - implementations of API rest client, supporting synchronous and asynchronous access to BNB Beacon Chain's REST APIs.
* **[wallet](https://github.com/bnb-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/Wallet.java)** - management of accounts, including seed and encrypted mnemonic generation.

# How to get

1. Add the JitPack repository to your project's `pom.xml`:
    ```
        <repositories>
            <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
            </repository>
        </repositories>
    ```
2. Add the dependency:
    ```
	<dependency>
	    <groupId>com.github.bnb-chain</groupId>
	    <artifactId>java-sdk</artifactId>
	    <version>Tag</version>
	</dependency>
    ```

# Protobuf

The protobuf-maven-plugin is used in this SDK. It is a plugin that integrates protocol buffers compiler (protoc) into Maven lifecycle. The Plugin generates Java source files from .proto (protocol buffer definition) files for this project by running `mvn compile`. 

These Java source files will be packaged into the final artifact and referenced as imports from the dependent projects or modules by running `mvn clean package`.

More details please refer to https://github.com/xolstice/protobuf-maven-plugin

# API

For examples, please check the [wiki](https://github.com/bnb-chain/java-sdk/wiki/API).

# Testing

All new code changes should be covered with unit tests. You can see the existing test cases [here](https://github.com/bnb-chain/java-sdk/tree/master/src/test/java/com/binance/dex/api/client/encoding). 

# Contributing

Contributions to the BNB Beacon Chain Java SDK are welcome. Please ensure that you have tested the changes with a local client and have added unit test coverage for your code.
