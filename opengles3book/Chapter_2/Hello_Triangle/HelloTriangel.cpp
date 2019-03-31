#include "esUtil.h"
typedef struct {
	GLuint programObject;
}TestUserData;

int TestloadShader(GLenum type, const char *shaderSrc) {
	GLuint shader;
	GLuint complied;
	//������ɫ������
	shader = glCreateShader(type);
	if (shader == 0) {
		return 0;
	}
	//������ɫ��Դ����
	glShaderSource(shader, 1, &shaderSrc, NULL);
	//������ɫ��Դ����
	glCompileShader(shader);

	//�������״̬
	glGetShaderiv(shader, GL_COMPILE_STATUS, &complied);

	if (!complied) {
		GLint infoLen = 0;

		glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLen);

		if (infoLen > 1) {
			char *infoLog = malloc(sizeof(char) * infoLen);

			glGetShaderInfoLog(shader, infoLen, NULL, &infoLog);
			esLogMessage("Error compiling shader:\n%s\n", infoLog);

			free(infoLog);
		}
		glDeleteShader(shader);
		return 0;
	}
	return shader;
}

int TestInit(ESContext *esContext) {
	TestUserData *userData = esContext->userData;
	char vShaderStr[] = "#version 300 es \n"
		"layout (location = 0) in vec4 vPosition; \n"
		"void main() { \n"
		"glPosition = vPosition; \n"
		"}";
	char fShaderStr[] = "#version 300 es \n"
		"precision mediump float; \n"
		"out vec4 fragColor; \n"
		"void main() { \n"
		"fragColor = vec4(1.0, 0.0, 0.0, 1.0)"
		"} ";
	GLuint vertexShader;
	GLuint fragmentShader;
	GLuint programObject;
	GLuint linked;

	//���� ���� �� Ƭ��  ��ɫ��
	vertexShader = TestloadShader(GL_VERTEX_SHADER, vShaderStr);
	fragmentShader = TestloadShader(GL_FRAGMENT_SHADER, fShaderStr);

	//��������
	programObject = glCreateProgram();

	if (programObject == 0) {
		return 0;
	}


	glAttachShader(programObject, vertexShader);
	glAttachShader(programObject, fragmentShader);

	//���ӳ���
	glLinkProgram(programObject);
	
	//����������״̬
	glGetProgramiv(programObject, GL_LINK_STATUS, &linked);

	if (!linked) {
		GLint infoLen = 0;

		glGetProgramiv(programObject, GL_INFO_LOG_LENGTH, &infoLen);

		if (infoLen > 1) {
			char *infoLog = malloc(sizeof(char) * infoLen);

			glGetProgramInfoLog(programObject, infoLen, NULL, infoLog);
			esLogMessage("Eroor linking program:\n%s\n", infoLog);

			free(infoLog);
		}
		glDeleteProgram(programObject);
		return FALSE;
	}

	userData->programObject = programObject;

	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	return TRUE;
}

void TestDraw(ESContext *esContext) {
	TestUserData *userData = esContext->userData;
	GLfloat vVertices[] = {
		0.0f, 0.5f, 0.0f,
		-0.5f, -0.5f, 0.0f,
		0.5f, 0.5f, 0.0f
	};

	//����һ������
	glViewport(0, 0, esContext->width, esContext->height);

	//�����ɫ������
	glClear(GL_COLOR_BUFFER_BIT);

	//���ӳ���
	glUseProgram(userData->programObject);

	//���ض�����������
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, vVertices);
	glEnableVertexAttribArray(0);

	glDrawArrays(GL_TRIANGLES, 0, 3);

}

void TestShutdown(ESContext *esContext) {
	TestUserData *userData = esContext->userData;
	glDeleteProgram(userData->programObject);
}

int TestesMain(ESContext *esContext) {
	esContext->userData = malloc(sizeof(TestUserData));

	esCreateWindow(esContext, "Hello Triangel", 800, 800, ES_WINDOW_RGB);

	if (!TestInit(esContext)) {
		return GL_FALSE;
	}

	esRegisterShutdownFunc(esContext, TestShutdown);
	esRegisterDrawFunc(esContext, TestDraw);

	return GL_TRUE;
}