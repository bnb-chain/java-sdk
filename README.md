# Binance Chain Java SDK

The Binance Chain Java SDK works as a lightweight Java library for interacting with the [Binance Chain](https://binance-chain.github.io/api-reference/dex-api/paths.html). It provides a complete API coverage, and supports synchronous and asynchronous requests.  It includes the following core components:

* **[crypto](https://github.com/binance-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/encoding/Crypto.java)** - core cryptographic functions.
* **[amino encoding](https://github.com/binance-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/encoding)** - [amino](https://github.com/binance-chain/docs-site/blob/master/docs/encoding.md) (protobuf-like) encoding and decoding of transactions.
* **[client](https://github.com/binance-chain/java-sdk/tree/master/src/main/java/com/binance/dex/api/client/impl)** - implementations of API rest client, supporting synchronous and asynchronous access to Binance Chain's REST APIs.
* **[wallet](https://github.com/binance-chain/java-sdk/blob/master/src/main/java/com/binance/dex/api/client/Wallet.java)** - management of accounts, including seed and encrypted mnemonic generation.


# Installation


1. Install library into your Maven's local repository by running `mvn install`
2. Add the following Maven dependency to your project's `pom.xml`:
```
<dependency>
    <groupId>com.binance.dex.api</groupId>
    <artifactId>binance-dex-api-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

# API

For examples, please check the [wiki](https://github.com/binance-chain/java-sdk/wiki).

# Testing

All new code changes should be covered with unit tests. You can see the existing test cases here: https://github.com/binance-chain/java-sdk/tree/master/src/test/java/com/binance/dex/api/client/encoding 


# Contributing

Contributions to the Binance Chain Java SDK are welcome. Please ensure that you have tested the changes with a local client and have added unit test coverage for your code.

