# SlideToggleView
A Android Button View With Slide Function To Switch

How To Use
Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
Step 2. Add the dependency

	dependencies {
	       implementation 'com.github.kuang2010:SlideToggleView:1.0.2'
	}
  
  

Step 3. add it in your layout xml file:


    <com.kuang2010.slidetoggle.view.SlideToggleView
        android:id="@+id/stv_demo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"/>
        
        
        
Step 4.  in activity:

        SlideToggleView stv_demo = findViewById(R.id.stv_demo);
        stv_demo.setOnToggleChangeListener(new SlideToggleView.OnToggleChangeListener() {
            @Override
            public void onToggleChange(View view, boolean open) {
                Toast.makeText(MainActivity.this,open?"打开":"关闭",Toast.LENGTH_SHORT).show();
            }
        });
        stv_demo.setToggleState(true);
        
