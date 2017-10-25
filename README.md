study-payara-java
=================

JavaEEサンプル

準備
----

payaraドメイン作成

```sh
ii $Home/apps/payara41/bin/asadmin
```
```
create-domain --keytooloptions CN=localhost test
start-domain test
login

set *.system-property.JMS_PROVIDER_PORT.description=''

```

payaraクラスタ／インスタンス作成

```
create-cluster AppleCluster
create-cluster OrangeCluster

create-instance --node localhost-test --cluster AppleCluster --portbase 50100 AppleInstance
create-instance --node localhost-test --cluster OrangeCluster --portbase 50200 OrangeInstance

```

* 管理ポート: portbase + 48
* HTTPリスナー・ポート: portbase + 80
* HTTPSリスナー・ポート: portbase + 81
* JMSポート: portbase + 76
* IIOPリスナー・ポート: portbase + 37
* セキュリティ保護されたIIOPリスナー・ポート: portbase + 38
* セキュリティ保護されたIIOP相互認証ポート: portbase + 39
* JMXポート: portbase + 86
* JPAデバッガ・ポート: portbase + 9
* OSGiモジュール管理用Felixシェル・サービス・ポート: portbase + 66


実行
----

payara起動

```sh
ii $Home/apps/payara41/bin/asadmin
```
```
start-instance AppleInstance
start-instance OrangeInstance
list-instances
```
```sh
LogExpert $Home/apps/payara41/glassfish/domains/test/logs/server.log `
    $Home/apps/payara41/glassfish/nodes/localhost-test/AppleInstance/logs/server.log `
    $Home/apps/payara41/glassfish/nodes/localhost-test/OrangeInstance/logs/server.log
```

アプリケーション配布

```sh
./gradlew clean assemble
```
```
undeploy --cascade=true --target AppleCluster AppleEAR-1.0-SNAPSHOT
deploy --force=true --target AppleCluster 'C:/Users/Public/repos/git/study/study-payara-java/ears/AppleEAR/build/libs/AppleEAR-1.0-SNAPSHOT.ear'

undeploy --cascade=true --target OrangeCluster OrangeEAR-1.0-SNAPSHOT
deploy --force=true --target OrangeCluster 'C:/Users/Public/repos/git/study/study-payara-java/ears/OrangeEAR/build/libs/OrangeEAR-1.0-SNAPSHOT.ear'

```

テスト実行

```sh
./gradlew jars:EmbeddedTest:test -i
ii ./jars/EmbeddedTest/build/reports/tests/test/index.html

./gradlew jars:RemoteTest:test -i
ii ./jars/RemoteTest/build/reports/tests/test/index.html

```


補足
----

EmbeddedTest用のdomein.xmlファイル作成

```sh
ii $Home/apps/payara41/bin/asadmin --port 50048
```
```
create-domain --portbase 50000 --keytooloptions CN=localhost test
start-domain test
login

set *.system-property.JMS_PROVIDER_PORT.description=''

create-cluster AppleCluster
create-instance --node localhost-test --cluster AppleCluster AppleInstance
delete-instance AppleInstance
delete-cluster AppleCluster

```


参考
----

* [arquillian glassfish EJB - Google 検索](https://www.google.co.jp/search?q=arquillian+glassfish+EJB&newwindow=1&source=lnt&tbs=lr:lang_1ja&lr=lang_ja&sa=X&ved=0ahUKEwjD6YSFh8jWAhWFJZQKHfEPAmwQpwUIHw&biw=1304&bih=702)
* [結合テスト - Web開発芸術 Learn Once, Practice Anywhere.](https://sites.google.com/site/webdevelopart/30-common/integration-test)
* [Reference Guide - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/Reference+Guide)
* [Multiple Containers - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/Multiple+Containers)
* [GlassFish 3.1 - Remote - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/GlassFish+3.1+-+Remote)
* [GlassFish 3.1 - Embedded - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/GlassFish+3.1+-+Embedded)
