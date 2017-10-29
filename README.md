study-payara-java
=================

JavaEEサンプル

準備
----

payaraドメイン作成

```sh
# 管理コンソール起動
PS> & $Home/apps/payara41/bin/asadmin

# ドメイン作成
asadmin> create-domain --keytooloptions CN=localhost test
asadmin> start-domain test
asadmin> login
```

管理用WEBコンソール起動、ログイン
※起動、ログインによりデフォルト値を生成されてdomain.xmlに反映される

http://localhost:4848

```sh
# payara-mlの場合、日本語コメントが起動エラーの原因になるため削除する
asadmin> set *.system-property.JMS_PROVIDER_PORT.description=''

# payaraクラスタ作成
asadmin> create-cluster AppleCluster
asadmin> create-cluster OrangeCluster

# payaraインスタンス作成
asadmin> create-instance --node localhost-test --portbase 20000 TestInstance
asadmin> create-instance --node localhost-test --cluster AppleCluster --portbase 50100 AppleInstance
asadmin> create-instance --node localhost-test --cluster OrangeCluster --portbase 50200 OrangeInstance
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
# 管理コンソール起動
PS> & $Home/apps/payara41/bin/asadmin

# ドメイン起動
asadmin> start-domain test

# インスタンス起動
asadmin> start-instance TestInstance
asadmin> start-instance AppleInstance
asadmin> start-instance OrangeInstance

# 確認
asadmin> list-instances

# ログ表示
PS> LogExpert $Home/apps/payara41/glassfish/domains/test/logs/server.log `
    $Home/apps/payara41/glassfish/nodes/localhost-test/TestInstance/logs/server.log `
    $Home/apps/payara41/glassfish/nodes/localhost-test/AppleInstance/logs/server.log `
    $Home/apps/payara41/glassfish/nodes/localhost-test/OrangeInstance/logs/server.log
```

アプリケーション配布

```sh
# 配布アプリケーション作成
PS> ./gradlew clean assemble

# AppleEAR配布
asadmin> undeploy --cascade=true --target AppleCluster AppleEAR-1.0-SNAPSHOT
asadmin> deploy --force=true --target AppleCluster 'C:/Users/Public/repos/git/study/study-payara-java/ears/AppleEAR/build/libs/AppleEAR-1.0-SNAPSHOT.ear'

# OrangeEAR配布
asadmin> undeploy --cascade=true --target OrangeCluster OrangeEAR-1.0-SNAPSHOT
asadmin> deploy --force=true --target OrangeCluster 'C:/Users/Public/repos/git/study/study-payara-java/ears/OrangeEAR/build/libs/OrangeEAR-1.0-SNAPSHOT.ear'
```

テスト実行

```sh
# payara-embeddedによるテスト
PS> ./gradlew test:EmbeddedTest:test -i
PS> ii ./test/EmbeddedTest/build/reports/tests/test/index.html

# payra-serverによるテスト
PS> ./gradlew test:RemoteTest:test -i
PS> ii ./test/RemoteTest/build/reports/tests/test/index.html
```


補足
----

EmbeddedTest用のdomein.xmlファイル作成

```sh
# 管理コンソール起動
PS> & $Home/apps/payara41/bin/asadmin --port 50048

# ドメイン作成
asadmin> create-domain --portbase 50000 --keytooloptions CN=localhost test
asadmin> start-domain test
asadmin> login
```

管理用WEBコンソール起動、ログイン
※起動、ログインによりデフォルト値を生成されてdomain.xmlに反映される

http://localhost:50048

```sh
# payara-mlの場合、日本語コメントが起動エラーの原因になるため削除する
asadmin> set *.system-property.JMS_PROVIDER_PORT.description=''

# クラスタ作成／削除（デフォルト値を生成されてdomain.xmlに反映される）
asadmin> create-cluster dummy
asadmin> delete-cluster dummy
```


参考
----

* [arquillian glassfish EJB - Google 検索](https://www.google.co.jp/search?q=arquillian+glassfish+EJB&newwindow=1&source=lnt&tbs=lr:lang_1ja&lr=lang_ja&sa=X&ved=0ahUKEwjD6YSFh8jWAhWFJZQKHfEPAmwQpwUIHw&biw=1304&bih=702)
* [結合テスト - Web開発芸術 Learn Once, Practice Anywhere.](https://sites.google.com/site/webdevelopart/30-common/integration-test)
* [Reference Guide - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/Reference+Guide)
* [Multiple Containers - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/Multiple+Containers)
* [GlassFish 3.1 - Remote - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/GlassFish+3.1+-+Remote)
* [GlassFish 3.1 - Embedded - Arquillian - Project Documentation Editor](https://docs.jboss.org/author/display/ARQ/GlassFish+3.1+-+Embedded)
