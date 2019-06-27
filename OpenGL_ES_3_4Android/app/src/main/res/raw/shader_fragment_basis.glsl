#version 300 es
precision mediump float;//配置默认的精度限定符
in vec4 v_color;// 注意和 shader_vertex 中 out 颜色属性名相同
out vec4 frag_color;
void main(){
    frag_color = v_color;
}