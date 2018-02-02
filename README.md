# shakespeare-rnn

Generate Fakespeare with Clojure using [jutsu.ai][jutsu] and [DeepLearning4J][dl4j]

## Usage

First, download the complete works of Shakespeare in a file named "pg100.txt"
into this directory:

`$ wget https://www.gutenberg.org/cache/epub/100/pg100.txt`

Then start a repl and get training!

`$ lein repl`

`user=> (c/train 4 1)`

## Java Troubleshooting

If you get a `ClassNotFound` Exception on startup, there's probably a
Java compilation issue for the helper classes in `src/java`. A couple things
that have helped, noting that different things seem to work on different
platforms (not a great situation):

* Step down to Clojure 1.8
* Comment out the `:source-paths` line in `project.clj` and run `lein javac`

## License

Copyright © 2018 Joshua Miller

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[jutsu]: https://github.com/hswick/jutsu.ai
[dl4j]: https://deeplearning4j.org
