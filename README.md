# IronMQ Clojure Client

## Getting Started

Add dependency to your project.clj:

```clojure
[iron_mq_clojure "1.0.2"]
```

Require IronMQ client code:

```clojure
(require '[iron-mq-clojure.client :as mq])
```

Create IronMQ client:

```clojure
(def client (mq/create-client "YOUR_PROJECT_ID" "YOUR_TOKEN"))
```

For Rackspace:

```clojure
(def client (mq/create-client "YOUR_PROJECT_ID" "YOUR_TOKEN" :host mq/rackspace-host))
```

Now you can interact with IronMQ:

```clojure
(mq/post-message client "myqueue" "hello from clojure")

(let [msg (mq/get-message client "myqueue")]
  (if msg
    (do
      (println (get msg "body"))
      (mq/delete-message client "myqueue" msg))
    (println "queue is empty")))

```
