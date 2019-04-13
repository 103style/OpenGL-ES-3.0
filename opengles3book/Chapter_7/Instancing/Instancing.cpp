#include "esUtil.h"
#include<stdlib.h>
#include<math.h>

#ifdef _WIN32
#define srandom srand
#define random rand
#endif // _WIN32

#define NUM_INSTANCES  100
#define POSITION_LOC  0
#define COLOR_LOC  1
#define MVP_LOC  2

typedef struct
{
	//��ɫ������
	GLuint programObject;

	//���㻺��������
	GLuint positionVBO;
	GLuint colorVBO;
	GLuint mvpVBO;
	GLuint indicesIBO;

	//��������
	int numIndices;

	//��ת�Ƕ�
	GLfloat  angle[NUM_INSTANCES];

} UserData;

int _Init(ESContext *esContext)
{
	GLfloat *positions;
	GLuint *indices;

	UserData *userData = esContext->userData;

	//������ɫ��Դ����
	const char vShaderStr[] = "#version 300 es  \n"
		"layout(location = 0) in vec4 a_position; \n"
		"layout(location = 1) in vec4 a_color; \n"
		"layout(location = 2) in mat4 a_mvpMatix; \n"
		"out vec4 v_color; \n"
		"void main() \n"
		"{ \n"
		"	v_color = a_color; \n"
		"	gl_Postion = a_postion * a_mvpMatrix; \n"
		"}  \n";

	const char fShaderStr[] = "#version 300 es \n"
		"precision mediump float; \n"
		"in vec4 v_color; \n"
		"layout(location = 0)  out vec4 outColor; \n"
		"void main()  \n"
		"{  \n"
		"		outColot = v_color; \n"
		"}";

	//������ɫ�����벢���ӵ���ɫ������
	userData->programObject = esLoadProgram(vShaderStr, fShaderStr);

	//������������
	userData->numIndices = esGenCube(0.1f, &positions, NULL, NULL, &indices);

	//��������������
	glGenBuffers(1, userData->indicesIBO);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, userData->indicesIBO);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLuint) * userData->numIndices, indices, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	free(indices);

	//λ�û���������
	glGenBuffers(1, userData->positionVBO);
	glBindBuffer(GL_ARRAY_BUFFER, userData->positionVBO);
	glBufferData(GL_ARRAY_BUFFER, 24 * sizeof(GLfloat) * 3, positions, GL_STATIC_DRAW);
	free(positions);

	//��ÿ��ʵ�����������ɫ
	{
		GLubyte colors[NUM_INSTANCES][4];
		int instace;

		srand(0);

		for (instace = 0; instace < NUM_INSTANCES; instace++)
		{
			colors[instace][0] = random() % 255;
			colors[instace][1] = random() % 255;
			colors[instace][2] = random() % 255;
			colors[instace][3] = 0;
		}

		glGenBuffers(1, userData->colorVBO);
		glBindBuffer(GL_ARRAY_BUFFER, userData->colorVBO);
		glBufferData(GL_ARRAY_BUFFER, NUM_INSTANCES * 4, colors, GL_STATIC_DRAW);
	}

	//���䴢��ռ䱣��ÿ�� �任���� ʵ��
	{
		int instace;

		for (instace = 0; instace < NUM_INSTANCES; instace++)
		{
			userData->angle[instace] = (float)(random() % 32768) / 32767.0f * 360.0f;
		}

		glGenBuffers(1, userData->mvpVBO);
		glBindBuffer(GL_ARRAY_BUFFER, userData->mvpVBO);
		glBufferData(GL_ARRAY_BUFFER, NUM_INSTANCES * sizeof(ESMatrix), NULL, GL_DYNAMIC_DRAW);
	}
	glBindBuffer(GL_ARRAY_BUFFER, 0);

	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	return GL_TRUE;
}

//����ʱ���������� �任����
void Update(ESContext *esContext, float deltaTime)
{
	UserData *userData = esContext->userData;
	ESMatrix *matrixBuf;
	ESMatrix perspective;
	float aspect;
	int instance = 0;
	int numRows;
	int numColumn;

	//���㴰�ڵĿ�߱�
	aspect = (GLfloat)esContext->width / (GLfloat)esContext->height;

	esMatrixLoadIdentity(&perspective);
	esPerspective(&perspective, 60.0f, aspect, 1.0f, 20.0f);

	glBindBuffer(GL_ARRAY_BUFFER, userData->mvpVBO);
	matrixBuf = (ESMatrix *)glMapBufferRange(GL_ARRAY_BUFFER, 0, sizeof(ESMatrix) * NUM_INSTANCES, GL_MAP_WRITE_BIT);

	numRows = (int)sqrtf(NUM_INSTANCES);
	numColumn = numRows;

	for (instance = 0; instance < NUM_INSTANCES; instance++)
	{
		ESMatrix modelview;
		float translateX = ((float)(instance % numRows) / (float)numRows)*2.0f - 1.0f;
		float translateY = ((float)(instance / numColumn) / (float)numColumn) * 2.0f - 1.0f;

		esMatrixLoadIdentity(&modelview);

		esTranslate(&modelview, translateX, translateY, -2.0f);

		userData->angle[instance] += (deltaTime * 40.0f);

		if (userData->angle[instance] >= 360.0f)
		{
			userData->angle[instance] -= 360.0f;
		}

		//��ת������
		esRotate(&modelview, userData->angle[instance], 1.0, 0.0, 1.0);

		esMatrixMultiply(&matrixBuf[instance], &modelview, &perspective);
	}

	glUnmapBuffer(GL_ARRAY_BUFFER);
}

void Draw(ESContext *esContext)
{
	UserData *userData = esContext->userData;

	//���ô���
	glViewport(0, 0, esContext->width, esContext->height);

	//�����ɫ
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	//������ɫ������
	glUseProgram(userData->programObject);

	//���ض�������
	glBindBuffer(GL_ARRAY_BUFFER, userData->positionVBO);
	glVertexAttribPointer(POSITION_LOC, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (const void *)NULL);
	glEnableVertexAttribArray(POSITION_LOC);

	//����ʵ����ɫ������
	glBindBuffer(GL_ARRAY_BUFFER, userData->colorVBO);
	glVertexAttribPointer(COLOR_LOC, 4, GL_UNSIGNED_BYTE, GL_TRUE, 4 * sizeof(GLubyte), (const void *)NULL);
	glEnableVertexAttribArray(COLOR_LOC);
	glVertexAttribDivisor(COLOR_LOC, 1);

	//����ʵ�� �任���� ������
	glBindBuffer(GL_ARRAY_BUFFER, userData->mvpVBO);

	//���ؾ����ÿһ��
	glVertexAttribPointer(MVP_LOC + 0, 4, GL_FLOAT, GL_FALSE, sizeof(ESMatrix), (const void *)NULL);
	glVertexAttribPointer(MVP_LOC + 1, 4, GL_FLOAT, GL_FALSE, sizeof(ESMatrix), (const void *)(sizeof(GLfloat) * 4));
	glVertexAttribPointer(MVP_LOC + 1, 4, GL_FLOAT, GL_FALSE, sizeof(ESMatrix), (const void *)(sizeof(GLfloat) * 8));
	glVertexAttribPointer(MVP_LOC + 1, 4, GL_FLOAT, GL_FALSE, sizeof(ESMatrix), (const void *)(sizeof(GLfloat) * 12));

	glEnableVertexAttribArray(MVP_LOC + 0);
	glEnableVertexAttribArray(MVP_LOC + 1);
	glEnableVertexAttribArray(MVP_LOC + 2);
	glEnableVertexAttribArray(MVP_LOC + 3);

	glVertexAttribDivisor(MVP_LOC + 0, 1);
	glVertexAttribDivisor(MVP_LOC + 1, 1);
	glVertexAttribDivisor(MVP_LOC + 2, 1);
	glVertexAttribDivisor(MVP_LOC + 3, 1);

	//��������������
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, userData->indicesIBO);

	//����������
	glDrawElementsInstanced(GL_TRIANGLES, userData->numIndices, GL_UNSIGNED_INT, (const void *)NULL, NUM_INSTANCES);
}

void Shutdown(ESContext *esContext)
{
	UserData *userData = esContext->userData;

	glDeleteBuffers(1, userData->positionVBO);
	glDeleteBuffers(1, userData->colorVBO);
	glDeleteBuffers(1, userData->mvpVBO);
	glDeleteBuffers(1, userData->indicesIBO);

	//ɾ����ɫ������
	glDeleteProgram(userData->programObject);
}

int esMain(ESContext *esContext)
{
	esContext->userData = malloc(sizeof(UserData));

	esCreateWindow(esContext, "Instancing", 640, 480, ES_WINDOW_RGB | ES_WINDOW_DEPTH);

	if (!_Init(esContext))
	{
		return GL_FALSE;
	}

	esRegisterShutdownFunc(esContext, Shutdown);
	esRegisterUpdateFunc(esContext, Update);
	esRegisterDrawFunc(esContext, Draw);

	return GL_TRUE;
}

