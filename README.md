# IronMQ Clojure Client

## Getting Started

Add dependency to your project.clj:

```clojure
[iron_mq_clojure "1.0.1"]
```

Require IronMQ client code:

```clojure
(require '[iron-mq-clojure.client :as imqc])
```

Create IronMQ client:

```clojure
(def client (imqc/create-client "YOUR_PROJECT_ID" "YOUR_TOKEN"))
```

For Rackspace:

```clojure
(def client (imqc/create-client "YOUR_PROJECT_ID" "YOUR_TOKEN" :host imqc/rackspace-host))
```

Now you can interact with IronMQ:

```clojure
(imqc/post-message client "myqueue" "hello from clojure")

(let [msg (imqc/get-message client "myqueue")]
  (if msg
    (do
      (println (get msg "body"))
      (imqc/delete-message client "myqueue" msg))
    (println "queue is empty")))

```
