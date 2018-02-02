(defproject shakespeare-rnn "0.1.0-SNAPSHOT"
  :description "Generate Fakespeare with DL4J & Clojure"
  :url "https://github.com/joshuamiller/shakespeare-rnn"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["dev" "src/clj"]
  :java-source-paths ["src/java"]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [hswick/jutsu.ai "0.1.1"]
                 ;; Replace with [org.nd4j/nd4j-cuda-8.0-platform "0.8.0"] for GPU
                 [org.nd4j/nd4j-native-platform "0.8.0"]
                 [org.clojure/tools.namespace "0.3.0-alpha4"]
                 ;; Here for Java 1.8 compat
                 [org.projectlombok/lombok "1.16.20"]])
