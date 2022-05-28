(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]])
  (:gen-class))

(defn greet [_]
  {:status 200
   :body "Hello, Universe!"
   :headers {}})

(defn goodbye [_]
  {:status 200
   :body "Goodbye, Cruel World!"
   :headers {}})

(defroutes app
  (GET "/" [] greet)
  (GET "/goodbye" [] goodbye)
  (not-found "Page not found."))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [{:keys [port]}]
  (jetty/run-jetty (wrap-reload #'app) 
                   {:port (Integer. port)}))
