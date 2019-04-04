# GYFrame
源码框架集合

本人为学习工作方便，集成了许多Github大牛完成出色项目，如果涉及到版权问题请与我联系，相关框架请查看 README_*.md

Step 1. Add the JitPack repository to your build file 
gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Copy
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.GaoYan8:GYFrame:Tag'
	}
Copy
Share this release:
