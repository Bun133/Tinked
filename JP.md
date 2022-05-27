# Tinkedとは

Tinkedは、プラグイン内で発生する複数Tickをまたがる処理をThenなどの方法で、チェーンする記法を提供するライブラリです。

Tinkedを利用することで例えば、プレイヤーからのアクション(テキストの入力やクリックなど)を受け取り、それに応じて処理を行うことを一つのチェーンで記述することができます。

# 基本的な使い方

基本的にTinkedのあらゆる処理は[Task](https://github.com/Bun133/Tinked/blob/main/src/main/java/com/github/bun133/tinked/Task.kt)
を継承する必要があります。

また、処理のうち複数Tickにまたがる*可能性が少しでもあるもの*
は[TickedTask](https://github.com/Bun133/Tinked/blob/main/src/main/java/com/github/bun133/tinked/TickedTask.kt)を継承するべきです。

# Then

Tinkedライブラリには`Then`という関数があります。

この関数は[Task](https://github.com/Bun133/Tinked/blob/main/src/main/java/com/github/bun133/tinked/Task.kt)
に実装されており、前のタスクの返り値を受け取り、それを次のタスクに渡します。

(Tinkedにおける`Then`は、ただ1つのTaskをチェーンするために使うべきであり、2つ以上のTaskをチェーンすることはできません)

```kotlin
RunnableTask {
    // FirstTask
}.apply {
    then(RunnableTask {
        // SecondTask
    })
}
```

# Apply

`Apply`は`Then`と似ていますが、少し違うところがあります。

この関数も`Then`と同じく[Task](https://github.com/Bun133/Tinked/blob/main/src/main/java/com/github/bun133/tinked/Task.kt)
に実装されていますが、このタスクは2つのタスクを1つのタスクにするために使われます。

```kotlin
// entireTask(receive I,return R)
val entireTask: Task<I, R> = RunnableTask<I, V> {
    // FirstTask(receive I,return V)
}.apply(RunnableTask<V, R> {
    // SecondTask(receive V,return R)
})

```

# 実行方法

上の例でも示したように、Tinkedの`Then`は常に**チェーンの最後のタスク**を返します。

実行する際はそのことに気をつけて、Kotlinでは`apply`などを使用してください。

実際にTaskを実行するには、`run`を呼び出します。

```kotlin
val task = RunnableTask { s: String ->
    println(s)
}

task.run("Hello World")
```

# TinkedにおけるTaskクラスのおすすめ設計

・ステートレスであること(つまり、Taskのインスタンスは使いまわさない)

・タスクの失敗時には例外を投げること(むやみにreturnTypeをNullableにしたりしない)

・タスクの終了時にすべてのリソースを解放すること(BukkitのrunTaskTimerなどを最後に解除する)

# 自分独自のTaskを作る

多くの場合、自分独自のTaskを作ることになると思います。

その際には上記のおすすめ設計について考えたのち、[Task](https://github.com/Bun133/Tinked/blob/main/src/main/java/com/github/bun133/tinked/Task.kt)
あるいは[TickedTask](https://github.com/Bun133/Tinked/blob/main/src/main/java/com/github/bun133/tinked/TickedTask.kt)
を継承したクラスを作成してください。

その際、実装しなければいけないメソッドは以下の通りです。

1. `run(input)`
2. `runnable(input):returnType`

## run(input)

実際にタスクを実行するメソッドです。

このタスクの内容をここで処理し、処理成功時には次のタスクにその値を渡します。

```kotlin
class MyTask : Task<String, String>() {
    override fun run(input: String) {
        println(input)          // 処理内容
        nextNode?.run(input)    // 次のタスクに値を渡す
    }
}
```

## runnable(input):returnType

これは実装せず、exceptionを投げるような実装でかまいません。

ですがその際に、`run(input)`をoverrideし、この関数を呼ばないようにしてください。

```kotlin
class MyTask : Task<String, String>() {
    override fun runnable(input: String): String {
        // This method is not reachable
        throw NotImplementedError()
    }
}
```