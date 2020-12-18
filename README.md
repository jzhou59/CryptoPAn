```
/**
 * Copyright 2020 JunjieZhou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```
# CryptoPAn
## Introduction
CryptoPAn is a prefix preserving anonymization algorithm specified for IPv4.
Network trace capture files or logs contain underlying information and can be applied into appropriate research, such as security analysis and commercial plan. However, explicit information concerning privacy issues also exists in such files, which hinders outsourcing them to a third party for the purpose of research. IP address is a typical of privacy information due to its direct reflection on host location.
To protect IP address from revealing when publish or outsource trace files, appropriate anonymization tools should be applied on IP address. Xu et. al[^1] proposed a cryptographic-based prefix-preserving scheme, so-called CryptoPAn, to anonymize IP addresses, meanwhile retaining the prefix stucture of them.
The source prototype of CryptoPAn implemented in cpp was published in Gatech website, but it is off shelf now. However, the source code are redistributed by many people and could be found in [^2][^3]. This project provides a java version of CryptoPAn.
## CryptoPAn Mechanism
Anonymize original IP addresses to obfuscated one, CryptoPAn is proved to be a deterministic one-to-one mapping. It can sanitize IP addresses and, at the same time, preserve prefix relationships among them. Besides, it effectively utilizes prefix relationship of orignal IP addresses and ensures the prefix preserving property accurate to every single bit. It is noted that a 32-height complete binary tree can represent entire set of possible distinct IPv4 addresses. As [^1] illustrated, Figure 1(a) shows a simplified IP addresses space using a complete binary tree. Figure 1(b) shows an orignial address tree including 9 IP addresses for example. 

## Other Implementation of CryptoPAn

## References
[^1]: Xu, J., Fan, J., Ammar, M. H., & Moon, S. B. (2002, November). Prefix-preserving ip address anonymization: Measurement-based security evaluation and a new cryptography-based scheme. In *10th IEEE International Conference on Network Protocols, 2002. Proceedings*. (pp. 280-289). IEEE.
[^2]: *NetSniff*. Caia.swin.edu.au. (2020). Retrieved 18 December 2020, from http://caia.swin.edu.au/ice/tools/netsniff/.
[^3]: *certtools/cryptopanlib*. GitHub. (2020). Retrieved 18 December 2020, from https://github.com/certtools/cryptopanlib.
[^4]:
[^5]:


## Project Structure
```
CryptoPAn
│   .gitignore : git ignore file
│   LICENSE : apache license
│   README.md : current file
│
├───.vscode
│       launch.json : launch file for java in vscode
│
├───API : java doc related files
│   │   allclasses-frame.html
│   │   allclasses-noframe.html
│   │   constant-values.html
│   │   deprecated-list.html
│   │   help-doc.html
│   │   index-all.html
│   │   index.html
│   │   overview-tree.html
│   │   package-list
│   │   script.js
│   │   serialized-form.html
│   │   stylesheet.css
│   │
│   └───org
│       └───datanon
│           └───CryptoPAn
│                   AES.html
│                   CryptoPAn.html
│                   CryptoPAnRunTimeException.html
│                   package-frame.html
│                   package-summary.html
│                   package-tree.html
│
├───lib
│       CryptoPAn.jar : jar package
│
└───src
    │   sampleTest.java : test class for CryptoPAn
    │
    └───org
        └───datanon
            └───CryptoPAn
                    AES.java : AES module used in CryptoPAn
                    CryptoPAn.java : CryptoPAn
                    CryptoPAnRunTimeException.java : Exception file
```
