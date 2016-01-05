# WARNING: THIS LIBRARY IS DEPRECATED. IT WILL NOT WORK WITH LATEST VERSION OF IRONMQ. 

# IronMQ Clojure Client

## Getting Started

Add dependency to your project.clj:

```clojure
[iron_mq_clojure "1.0.3"]
```

Require IronMQ client code:

```clojure
(require '[iron-mq-clojure.client :as mq])
```

Create IronMQ client:

```clojure
(def client (mq/create-client "YOUR_TOKEN" "YOUR_PROJECT_ID"))
```

For Rackspace:

```clojure
(def client (mq/create-client "YOUR_TOKEN" "YOUR_PROJECT_ID" :host mq/rackspace-host))
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
