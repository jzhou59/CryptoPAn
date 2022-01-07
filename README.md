# CryptoPAn
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) [![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://github.com/jzhou59/CryptoPAn/releases) [![Programming language](https://img.shields.io/badge/language-java-blue)](https://img.shields.io/badge/language-java-blue) [![JitPack](https://www.jitpack.io/v/jzhou59/CryptoPAn.svg)](https://www.jitpack.io/#jzhou59/CryptoPAn)
## Intro
CryptoPAn is an crypto-based anonymization algorithm for IP address. It is originally implemented by C/C++. This repo provides an alternative of **Java** version.
Details and other implementations could be found in [docs/CryptoPAn](docs/CryptoPAn.md) or [gh-pages for CryptoPAn](http://www.junjiezhou.cn/CryptoPAn/).

## Usage

**maven import**
1. add jitpack.io repository
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```
2. add cryptopan dependency
```xml
<dependency>
    <groupId>com.github.jzhou59</groupId>
    <artifactId>CryptoPAn</artifactId>
    <version>v0.0.1</version>
</dependency>
```
**java example**
```Java
try {
    CryptoPAn cryptoPAn = new CryptoPAn("wordlessthanfoursavecodefromboom");//the parameter is key for AES, length of it should be 128/192/256 bits
    String anonymizedIP = cryptoPAn.forward("199.32.34.122");
    System.out.print(anonymizedIP);
} catch (Exception e) {
    e.printStackTrace();
}
```

## License
```
Copyright 2020 JunjieZhou

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
