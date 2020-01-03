# VSSE

verifiable searchable symmetric encryption (database

## Paper

Towards Efficient Verifiable Conjunctive Keyword Search for Large Encrypted Database

## Tech Stack

- Language: Java `11.X`
- MySQL: `8.0+`
- Package Control manager: maven

## Project Structure

### jPBC

该库中的`Element`计算过程会直接**更改 Element**自身, 所以必须使用`duplicate()`.

### /Init

用于项目新开始所需要执行的步骤\
jPBC initial link refer [this](http://gas.dia.unisa.it/projects/jpbc/docs/pairing.html)

- ParamsPropertiesGenerator 用于创建 jPBC 的基础参数,`没有对TYPE_A.properties的特殊需求不需要启动`, 花费时间比较长(实际上 MBP-15'花了 2Hours+没有跑出来)
- 提高效率，只用 Bilinear 话，在 Pairing 之前加入此代码:`PairingFactory.getInstance().setUsePBCWhenPossible(true);` [Ref](http://gas.dia.unisa.it/projects/jpbc/docs/pairing.html#.XcFfM5Iza2A)
- PublicKey Gen: 发现原先代码指数 exp 错误, 已改正 12-25-2019

### /db

数据库相关操作.

Efficiency: sql 语句预处理 [sql prepared statement]

### /utils

> Clusion
>
> CryptoPrimitives  
> Printer print message by level

### /test

测试文件, 使用 JUnit4, 参考自[JUnit4](junit1)

### resources 资源

如何读取参考 [resource](rsc)

### params

由 jPBC 自带，暂时只用 typeA 的参数`a.properties`

- [ ] init/ParamsPropertiesGenerator 做测试;

## Problem & Improve

### Alg.1 EDBSetup

1. [ ] 大素数源代码`p`,`q`和乘积`n`均为 0;
2. [ ] utils/SerializableElement/readObject 判定`存疑`🤨, 不清楚他的具体判断;
3. [ ] PRF /Init/MasterKey 可以只写一个 byte 作 random;
4. [ ] 论文中的 ${ Z_n^* }$ 和 jpbc 中的 ${Z_r^*}$ 区别很大, 论文中 ${n = p \times q}$, 如果有 ${Z_r}$ 域可以选择 mod 就好了;
5. [x] 论文中 secret key ${sk = s}$ 是怎么生成的?;\
        ${\mathbb{G}}$ 域生成即可(by Wang.); 使用过程中用了 ${\mathbb{Z_r}}$
6. [x] ${z \leftarrow F_p(K_Z,g^{\frac{1}{w}} \textnormal{ mod } n \parallel c)}$
   - ${c}$ 是`级联`;

### Alg.2 TokenGen

1. line-2: ${1/w_1}$ 中 ${w_1}$ str2byte;
2. 论文里面的 ${K_T}$ 为笔误;
3. [ ] line-3 暂时设置为 keyword 固定对应的 fileId 数量(count);\
        BETTER flexible 的方式可以在 Setup 的过程中 data owner 创建每个 keyword(encrypted)和其对应的文件数量, 到时候直接查询即可减少大量的循环.
4. [ ] line-5 g^exp 需要 pow 还是 powZn(jpbc)?

### DB

1. [ ] insertKeyPairSet
       与 WangScheme 差别比较大, 均使用了原生函数. ATTENTION need check

### Alg.3 etc

## TODO

1. [ ] db/DBModule 利用`BloomFilter`判断 XSet 是否在 XSets;
2. [ ] 若知道匹配次数匹配次数, 则可实现[pre-processing];
3. [ ] 重写暂时不用`多线程`操作.
4. [ ] 启用 MySQL 连接池,降低多次连接资源开销(T2)

## Based on links

- [SSE-Clusion](https://github.com/encryptedsystems/Clusion) > Google Guava
- [preLab-crypt-misc](https://github.com/zhangzhongjun/CryptographyRepository)
- [jPBC-paper](https://ieeexplore.ieee.org/document/5983948/?arnumber=5983948)
- [jPBC-doc](http://gas.dia.unisa.it/projects/jpbc/docs)
- [jPBC-demos](https://www.programcreek.com/java-api-examples/?api=it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory)

[jdbc1]: https://www.cnblogs.com/Qian123/p/5339164.html
[junit1]: https://juejin.im/post/5c7fbfdd6fb9a049ef275a60
[rsc]: https://www.mkyong.com/java/java-read-a-file-from-resources-folder/
[sql prepared statement]: https://dev.mysql.com/doc/refman/8.0/en/sql-prepared-statements.html
[pre-processing]: http://gas.dia.unisa.it/projects/jpbc/docs/pairing.html
