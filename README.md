# Breaking the Vigenere cipher

This is a small project I completed at Supelec (2013). One of the first times I wrote some Java! The aim was to implement the [Vigenere cipher](https://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher) and to come up with a way of breaking it.

I first implemented the [Caesar cipher](https://en.wikipedia.org/wiki/Caesar_cipher), which is one of the simplest and most widely known encryption techniques. 
It is a type of substitution cipher in which each letter in the plaintext is replaced by a letter some fixed number of positions down the alphabet. 

The Vigenère cipher enhances the Caesar cipher by encrypting with a keyword or key phrase and not a single letter.
This means that the shift value changes cyclically during the encryption, rendering a simple frequency attack useless!

The Vigenère cipher is simple enough to be a field cipher if it is used in conjunction with cipher disks. The Confederate States of America, for example, used a brass cipher disk to implement the Vigenère cipher during the American Civil War. 
The Vigenère cipher gained a reputation for being exceptionally strong. Noted author and mathematician Charles Lutwidge Dodgson (Lewis Carroll) called the Vigenère cipher unbreakable in his 1868 piece "The Alphabet Cipher" in a children's magazine. In 1917, Scientific American described the Vigenère cipher as "impossible of translation". 

This reputation was not deserved. In the second part of the project, I successfully implemented an attack against the cipher, using classical cryptanalysis work by Kasiski.

## Source code

The Java source code contains classes to encrypt and decrypt a text file given a key.
* `Chiffre` (French for "cipher") is the a simple interface. A cipher must simply implement `encrypt` and `decrypt` methods.
* `ChiffreCesar` implements the Caesar cipher.
* `ChiffreVigenere` uses the Caesar cipher to implement the Vigenere cipher.

It also contains a `Dechiffreur` class ("decipherer") which can decrypt an encrypted without the key. 
It uses Kasiski examination to find the most likely key length and then frequency analysis to break the code.

Finally, `Programme` contains the main function, which encrypts a text file using a key and show how `Dechiffreur` breaks it.

## Data 

The text file I use in the demo are extracts from "Les Misérables" and from a Jules Vernes novel.
