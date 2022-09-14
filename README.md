[![](https://jitpack.io/v/shenguojun/GlidePaletteBlur.svg)](https://jitpack.io/#shenguojun/GlidePaletteBlur)

# GlidePaletteBlur

Combined with Glide to generate palette color or blur bitmap, Gif supported.

<img src="./Screenshot.jpg" alt="Screenshot" width=30% height=30% />

## How to use
1. For Palette:

   ```kotlin
   Glide.with(view)
       .load(image)
       .listener(GlidePalette().generate { palette ->
           palette?.darkColor()?.let { paletteColor ->
               view.ofTypeParent<ConstraintLayout>()?.setBackgroundColor(paletteColor)
           }
       })
       .into(view)
   ```

2. For blur:

   ```kotlin
   Glide.with(view)
       .load(image)
       .listener(GlideBlur().generate { bitmap ->
           view.ofTypeParent<ConstraintLayout>()?.background =
               BitmapDrawable(resources, bitmap)
       })  
       .into(view)
   ```

   

3. Add to project:

   ```groovy
   allprojects {
     repositories {
       ...
         maven { url 'https://jitpack.io' }
     }
   }
   ```

4. Add dependency:

   ```groovy
   dependencies {
     implementation 'com.github.shenguojun:GlidePaletteBlur:v1.0.0'
   }
   ```

Have fun!
