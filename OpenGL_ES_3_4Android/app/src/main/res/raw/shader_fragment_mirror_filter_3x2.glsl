#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
void main() {
    vec2 uv = vTexCoord;
    if (uv.x <= 0.34) {
        uv.x = uv.x * 3.0;
    } else if (uv.x <= 0.67) {
        uv.x = (uv.x - 0.34) * 3.0;
    } else {
        uv.x = (uv.x - 0.67) * 3.0;
    }
    if (uv.y <= 0.5) {
        uv.y = uv.y * 2.0;
    } else {
        uv.y = (uv.y - 0.5) * 2.0;
    }
    vFragColor = texture(uTextureUnit, fract(uv));
}