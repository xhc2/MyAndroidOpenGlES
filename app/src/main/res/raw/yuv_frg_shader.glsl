precision  mediump float;
varying  vec2 vTexCoord;
uniform sampler2D yTexture;
uniform sampler2D uTexture;
uniform sampler2D vTexture;
void main() {
     vec3 yuv;
     vec3 rgb;
     yuv.r = texture2D(yTexture, vTexCoord).r;
     yuv.g = texture2D(uTexture, vTexCoord).r - 0.5;
     yuv.b = texture2D(vTexture, vTexCoord).r - 0.5;
     rgb = mat3(1.0, 1.0, 1.0,
                 0.0, -0.39465, 2.03211,
                 1.13983, -0.58060, 0.0) * yuv;
      gl_FragColor = vec4(rgb, 1.0);
}