//�� 6 - 1
//�ṹ����  
//����������� glVertexAttribPointerָ��4����������
//����Ӧ�ó���ʹ�ö��㻺�������󣬱���ʹ�ÿͻ��˶������飬��ʵ���������
//OpenGL ES 3.0֧�ֶ�������ֻ��Ϊ�˼��� OpenGL ES 2.0

#include "esUtil.h"

#define VERTEX_POS_SIZE  3
#define VERTEX_NORMAL_SIZE  3
#define VERTEX_TEXCOORD0_SIZE  2
#define VERTEX_TEXCOORD1_SIZE  2

#define VERTEX_POS_INDX  0
#define VERTEX_NORMAL_INDX  1
#define VERTEX_TEXCOORD0_INDX  2
#define VERTEX_TEXCOORD1_INDX  3

#define VERTEX_POS_OFFSET  0
#define VERTEX_NORMAL_OFFSET  3
#define VERTEX_TEXCOORD0_OFFSET  6
#define VERTEX_TEXCOORD1_OFFSET  8

#define VERTEX_ATTRIB_SIZE (VERTEX_POS_SIZE + VERTEX_NORMAL_SIZE +VERTEX_TEXCOORD0_SIZE +VERTEX_TEXCOORD1_SIZE)


float *p = (float*)malloc(numVertics * VERTEX_ATTRIB_SIZE * sizeof(float));

//position is vertex attribute 0
glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	p);

//normal is vertex attribute 1
glVertexAttribPointer(VERTEX_NORMAL_INDX, VERTEX_NORMAL_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	(p + VERTEX_NORMAL_OFFSET));

//texture coordinate 0 is vertex attribute 2
glVertexAttribPointer(VERTEX_TEXCOORD0_INDX, VERTEX_TEXCOORD0_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	(p + VERTEX_TEXCOORD0_OFFSET));


//texture coordinate 1 is vertex attribute 3
glVertexAttribPointer(VERTEX_TEXCOORD1_INDX, VERTEX_TEXCOORD1_SIZE,
	GL_FLOAT, GL_FALSE,
	VERTEX_ATTRIB_SIZE * sizeof(float),
	(p + VERTEX_TEXCOORD1_OFFSET));




