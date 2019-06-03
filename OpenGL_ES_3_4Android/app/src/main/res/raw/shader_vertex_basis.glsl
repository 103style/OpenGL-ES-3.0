#version 300 es
layout(location = 0) in vec4 a_positon;
layout(location = 1) in vec4 a_color;
out vec4 v_color;
void main(){
    gl_Position = a_positon;
    v_color = a_color;
    pl_PointeSize = 10.0;
}