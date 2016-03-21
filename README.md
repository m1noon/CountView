# CountView
A simple Android library to display animation of counting number.

# Image
![demo_simple]

# Usage

Layout file

```XML
<com.m1noon.countview.CountView
            android:id="@+id/count_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingTop="4dp"
            android:text="0"
            android:textColor="#666699"
            android:textSize="20sp"/>
```

Java Code

```JAVA
// Set up
countView.interpolator(new AccelerateDecelerateInterpolator())
                .formatCommaSeparated()
                .velocity(10)
                .maxDuration(3000);
                
// Count up
countView.up(100);

// Count down
countView.down(100);

// To specified value.
countView.to(1000);
```


# License
```
Copyright (C) 2016 m1noon

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[demo_simple]:https://github.com/m1noon/CountView/blob/master/art/count_view_sample.gif
