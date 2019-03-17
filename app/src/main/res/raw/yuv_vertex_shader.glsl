attribute  vec4 aPosition;
attribute  vec2 aTexCoord;
varying vec2 vTexCoord;
void main() {
      vTexCoord = vec2(aTexCoord.x, 1.0 - aTexCoord.y); //这个1.0 - aTexCoord.y 是让yuv图像倒一个方向。
      gl_Position = aPosition;
}