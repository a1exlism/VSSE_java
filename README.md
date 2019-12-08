# VSSE

verifiable searchable symmetric encryption (database

## Tech Stack

- Language: Java `11.X`
- MySQL: `8.0+`
- Package Control manager: maven

## Project Structure

### /Init

用于项目新开始所需要执行的步骤\
jPBC initial link refer [this](http://gas.dia.unisa.it/projects/jpbc/docs/pairing.html)

- ParamsPropertiesGenerator 用于创建 jPBC 的基础参数,`没有对TYPE_A.properties的特殊需求不需要启动`, 花费时间比较长(实际上 MBP-15'花了 2Hours+没有跑出来)
- 提高效率，只用 Bilinear 话，在 Pairing 之前加入此代码:`PairingFactory.getInstance().setUsePBCWhenPossible(true);` [Ref](http://gas.dia.unisa.it/projects/jpbc/docs/pairing.html#.XcFfM5Iza2A)

### /db

数据库相关操作.

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

1. [ ] EDBSetup 大素数源代码`p`,`q`和乘积`n`均为 0;
2. [ ] SerializableElement 直接改成 Element;
3. [ ] PRF /Init/MasterKey 可以只写一个byte作random
4. 

## TODO

1. [ ] 若知道匹配次数匹配次数, 则可实现[pre-processing](http://gas.dia.unisa.it/projects/jpbc/docs/pairing.html)
2. [ ] 重写暂时不用`多线程`操作.
3. [ ] 启用 MySQL 连接池,降低多次连接资源开销(T2)

## Based on links

- [SSE-Clusion](https://github.com/encryptedsystems/Clusion) > Google Guava
- [preLab-crypt-misc](https://github.com/zhangzhongjun/CryptographyRepository)
- [jPBC-paper](https://ieeexplore.ieee.org/document/5983948/?arnumber=5983948)
- [jPBC-doc](http://gas.dia.unisa.it/projects/jpbc/docs)
- [jPBC-demos](https://www.programcreek.com/java-api-examples/?api=it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory)

[jdbc1]: https://www.cnblogs.com/Qian123/p/5339164.html
[junit1]: https://juejin.im/post/5c7fbfdd6fb9a049ef275a60
[rsc]: https://www.mkyong.com/java/java-read-a-file-from-resources-folder/
