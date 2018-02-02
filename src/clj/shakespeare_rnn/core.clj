(ns shakespeare-rnn.core
  (:import [org.deeplearning4j.examples.recurrent.character
            CharacterIterator GravesLSTMCharModellingExample]
           java.nio.charset.Charset
           java.util.Random
           org.nd4j.linalg.factory.Nd4j)
  (:require [jutsu.ai.core :as ai]
            [clojure.string :as str]))

(def iterator
  (CharacterIterator.
   "pg100.txt"
   (Charset/forName "UTF-8")
   32
   2000
   (CharacterIterator/getMinimalCharacterSet)
   (Random.)))

(def shks-net-config
  [:optimization-algo :sgd
   :learning-rate 0.1
   :seed 12345
   :regularization true
   :l2 0.001
   :weight-init :xavier
   :updater :rmsprop
   :layers [[:graves-lstm [:n-in (.inputColumns iterator) :n-out 200 :activation :tanh]]
            [:graves-lstm [:n-in 200 :n-out 200 :activation :tanh]]
            [:rnn-output :mcxent [:n-in 200 :n-out (.totalOutcomes iterator) :activation :softmax]]]
   :backprop-type :truncated-bptt
   :t-b-p-t-t-forward-length 50
   :t-b-p-t-t-backward-length 50
   :pretrain false
   :backprop true])

(def network
  (-> shks-net-config
      ai/network
      ai/initialize-net))

(defn samples
  "Generate sample text of length `sample-size`, `num-samples` times."
  [network iterator sample-size num-samples]
  (str/join "\n" (GravesLSTMCharModellingExample/sampleCharactersFromNetwork nil network iterator (java.util.Random.) sample-size num-samples)))

(defn train
  "Trains the network on the text provided by the iterator. Generates a sample
   every `sample-per-mb` minibatches. Trains for `epochs` epochs."
  ([network iterator sample-per-mb epochs]
   (dotimes [i epochs]
     (let [mb-number (atom 0)]
       (while (.hasNext iterator)
         (let [ds (.next iterator)]
           (.fit network ds)
           (swap! mb-number inc)
           (if (= 0 (mod @mb-number sample-per-mb))
             (do
               (println "Minibatch" @mb-number "completed")
               (println "------Sample------\n" (samples network iterator 100 1)))))))
     (.reset iterator)
     (println "------Epoch" i "completed------\n")
     (println "------Sample------\n" (samples network iterator 100 1))))
  ([sample-per-mb epochs]
   (train network iterator sample-per-mb epochs)))
