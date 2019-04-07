//例 6-2
// 数组结构
//位置、法线、纹理坐标0和1都保存在单独的缓冲区中

#include "esUtil.h"

float *position = (float*)malloc(numVertices * VERTEX_POS_SIZE * sizeof(float));

float *normal = (float*)malloc(numVertices * VERTEX_NORMAL_SIZE * sizeof(float));

float *texcoord0 = (float*)malloc(numVertices * VERTEX_TEXCOORD0_SIZE * sizeof(float));

float *texcoord1 = (float*)malloc(numVertices * VERTEX_TEXCOORD1_SIZE * sizeof(float));

//position is vertex attribute 0
glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	position);

//normal is vertex attribute 1
glVertexAttribPointer(VERTEX_NORMAL_INDX, VERTEX_NORMAL_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	normal);

//texture coordinate 0 is vertex attribute 2
glVertexAttribPointer(VERTEX_TEXCOORD0_INDX, VERTEX_TEXCOORD0_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	texcoord0);


//texture coordinate 1 is vertex attribute 3
glVertexAttribPointer(VERTEX_TEXCOORD1_INDX, VERTEX_TEXCOORD1_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	texcoord1);
