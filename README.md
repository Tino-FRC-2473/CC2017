# CC2017
Chezy Champs 2017

# Jetson Setup
Since this is the first time using the Jetson on our team, we had to do a lot of setup. For the most part, we had to setup a lot of python modules/packages. We install numpy, scipy, matplotlib, tensorflow, keras, and some other libraries. We also had to install Jetpack via a host Ubuntu PC. This automatically install things like TensorRT, cuDNN and CUDA which let us work with neural networks. Finally, we had to install OpenCV on the Jetson. We chose to build from sources as OpenCV didn't work in any other way. We didn't have enough space to do this with the space the Jetson had allocated so we chose to boot off of a 64GB SD Card. 
Install OpenCV on Jetson: http://dev.t7.ai/jetson/opencv/
Run TX1 off of SD Card: http://www.jetsonhacks.com/2017/01/26/run-jetson-tx1-sd-card/
