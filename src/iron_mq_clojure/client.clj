(ns iron-mq-clojure.client
  (:use [cheshire.core :only [parse-string generate-string]])
  (:import (java.net URL)))

(defn create-client
  "FIXME"
  [project-id token & options]
  (let [default {:project-id  project-id
                 :token       token
                 :api-version 1
                 :scheme      "https"
                 :host        "mq-aws-us-east-1.iron.io"
                 :port        443}]
    (merge default (apply hash-map options))))

(defn create-message
  "FIXME"
  [body & options]
  (merge {:body body} (apply hash-map options)))

(defn request
  [client method endpoint body]
  (let [path (format "/%d/projects/%s%s"
                     (:api-version client)
                     (:project-id client)
                     endpoint)
        url (URL. (:scheme client)
                  (:host client)
                  (:port client)
                  path)
        conn (. url openConnection)]
    (. conn setRequestMethod method)
    (. conn setRequestProperty "Content-Type" "application/json")
    (. conn setRequestProperty "Authorization" (format "OAuth %s" (:token client)))
    (. conn setRequestProperty "User-Agent" "IronMQ Clojure Client")
    (if-not (empty? body)
      (. conn setDoOutput true))
    (. conn connect)
    (if-not (empty? body)
      (spit (. conn getOutputStream) body))
    (let [status (. conn getResponseCode)]
      (if (= status 200)
        (parse-string (slurp (. conn getInputStream)))
        (throw (Exception. (slurp (. conn getErrorStream))))))))

(defn queues
  "FIXME"
  [client]
  (map (fn [q] (get q "name"))
       (request client "GET" "/queues" nil)))

(defn queue-size
  "FIXME"
  [client queue]
  (get (request client "GET" (format "/queues/%s" queue) nil)
       "size"))

(defn post-messages
  "FIXME"
  [client queue & messages]
  (get (request client "POST" (format "/queues/%s/messages" queue)
                (generate-string {:messages (map
                                             (fn [m]
                                               (if (string? m)
                                                 (create-message m) m))
                                             messages)}))
       "ids"))

(defn post-message
  "FIXME"
  [client queue message]
  (first (post-messages client queue message)))

(defn get-messages
  "FIXME"
  [client queue n]
    (get (request client "GET" (format "/queues/%s/messages?n=%d" queue n) nil)
         "messages"))
    
(defn get-message
  "FIXME"
  [client queue]
  (first (get-messages client queue 1)))

(defn delete-message
  "FIXME"
  [client queue message]
  (request client "DELETE" (format "/queues/%s/messages/%s" queue (get message "id")) nil))
