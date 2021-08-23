# Changelog

## 1.1.2
API UPGRADE
* [\#120](https://github.com/binance-chain/java-sdk/pull/120) [RPC] [API] deco old rest transactions api, and add new rest transaction apis

Breaking Changes
* getTransactions(TransactionsRequest request) will return transactions with new data model TransactionPageV2
* getTransactions(String address) will return transactions for an address in last 24 hours with new data model TransactionPageV2

Please refer to TransactionConverterFactory.java to see the mappings of the fields between TransactionPageV2 and TransactionPage 

Please refer to TransactionExample.java and [API Doc](https://docs.binance.org/api-reference/dex-api/block-service.html) for more details, the underlying API [Migration Guide](https://github.com/binance-chain/docs-site/blob/block-service/docs/api-reference/dex-api/migration-guide.md) could be also useful 

New API
* getTransactionsInBlock(long blockHeight) will return transaction in a specific block. 


## 1.1.1
CHAIN UPGRADE
* [\#105](https://github.com/binance-chain/java-sdk/pull/105) [RPC] [API] support for the transfer of token ownership, and decode the new types of oracle claim package

## 1.1.0
CHAIN UPGRADE
* [\#88](https://github.com/binance-chain/java-sdk/pull/88) [RPC] [API] enable side chain governance transaction
* [\#89](https://github.com/binance-chain/java-sdk/pull/89) [RPC] [API] enable side chain unbind transaction, and modify the structure of claimMsg

## 1.0.7
CHAIN UPGRADE
* [\#86](https://github.com/binance-chain/java-sdk/pull/86) [RPC] [API] Add Pending match flag to Depth API response
* [\#80](https://github.com/binance-chain/java-sdk/pull/80) [RPC] [API] Support Mini Token