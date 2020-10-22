v0.0.1

Compressor
======

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Compressor-blue.svg?style=flat)](http://android-arsenal.com/details/1/3758)

![](https://github.com/gswqwow/Compressor/blob/master/ss.png)

Compressor is a lightweight and powerful android image compression library. Compressor will allow you to compress large photos into smaller sized photos with very less or negligible loss in quality of the image.

# Gradle

```groovy
dependencies {
    // implementation 'id.zelory:compressor:2.1.0'
    implementation project(':compressor')
}
```

# Let's compress the image size!

#### Compress Image File

```java
Compressor compressor = new Compressor();
compressor.compress(getContext(), file, null);
```

### I want custom Compressor!

```java
 ImageSource imageSource = ImageSource.create(file, null);
        Size size = imageSource.getImageInfo().size;
        Compressor compressor = new Compressor();
        Compression compression = new Compression();
        compression.resolution(size.width, size.height);
        compression.quality(80);
        compression.format(CompressFormat.WEBP);
        compression.size(2048, 0, 0);
        compressor.compress(getContext(), file, compression);
```

License
-------

    Copyright (c) 2016 Zetra.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.