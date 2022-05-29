(ns webdev.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.adapter.jetty :as jetty]
            [ring.handler.dump :refer [handle-dump]]
            [ring.middleware.reload :refer [wrap-reload]])
  (:gen-class))

(defn about [_]
  {:status 200
   :body "Hi! I'm Stef. This is a dummy app for learning Clojure web dev."
   :headers {}})

(defn greet [_]
  {:status 200
   :body "Hello, Universe!"
   :headers {}})

(defn goodbye [_]
  {:status 200
   :body "Goodbye, Cruel World!"
   :headers {}})

;; (defn request [req]
;;   {:status 200
;;    :body (pr-str req)
;;    :headers {}})

(defn yo [req]
  {:status 200
   :body (str "Yo! " (:name (:route-params req)) "!")
   :headers {}})

(defroutes app
  (GET "/" [] greet)
  (GET "/about" [] about)
  (GET "/goodbye" [] goodbye)
  (GET "/request" [] handle-dump)
  (GET "/yo/:name" [] yo)
  (not-found "Page not found."))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [{:keys [port]}]
  (jetty/run-jetty (wrap-reload #'app) 
                   {:port (Integer. port)}))
