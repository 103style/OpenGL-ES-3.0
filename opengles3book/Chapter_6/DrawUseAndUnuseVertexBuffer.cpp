#include "esUtil.h"

#define VERTEX_POS_SIZE  3
#define VERTEX_COLOR_SIZE  4

#define VERTEX_POS_INDX  0
#define VERTEX_COLOR_INDX  1

void DrawPrimitiveWithoutVBOs(GLfloat *vertices, GLint vtxStride, GLint numIndices, GLushort *indices)
{
	GLfloat *vtxBuf = vertices;

	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glEnableVertexAttribArray(VERTEX_POS_INDX);
	glEnableVertexAttribArray(VERTEX_COLOR_INDX);

	glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE, GL_FLOAT, GL_FALSE, vtxStride, vtxBuf);

	vtxBuf += VERTEX_POS_SIZE;

	glVertexAttribPointer(VERTEX_COLOR_INDX, VERTEX_COLOR_SIZE, GL_FLOAT, GL_FALSE, vtxStride, vtxBuf);

	glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_SHORT, indices);

	glDisableVertexAttribArray(VERTEX_POS_INDX);
	glDisableVertexAttribArray(VERTEX_COLOR_INDX);
}

void DrawPrimitiveWithVBOs(ESContext *esContext, GLint numVertices, GLfloat *vtxBuf, GLint vtxStride, GLint numIndices, GLushort *indices)
{
	UserData *userData = (UserData*)esContext->userData;
	GLuint offset = 0;

	//vboIds[0] : 保存顶点属性数据 
	//vboIds[1] : 保存元素索引
	if (userData->vboIds[0] == 0 && userData->vboIds[1] == 0)
	{
		//只在第一次绘制时分配
		glGenBuffers(2, userData->boIds);

		glBindBuffer(GL_ARRAY_BUFFER, userData->vboIds[0]);
		glBufferData(GL_ARRAY_BUFFER, vtxStride * numIndices, vtxBuf, GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, userData->vboIds[1]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLushort) * numIndices, indices, GL_STATIC_DRAW);
	}

	glBindBuffer(GL_ARRAY_BUFFER, userData->vboIds[0]);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, userData->vboIds[1]);

	glEnableVertexAttribArray(VERTEX_POS_INDX);
	glEnableVertexAttribArray(VERTEX_COLOR_INDX);

	glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE, GL_FLOAT, GL_FALSE, vtxStride, (const void*)offset);

	offset += VERTEX_POS_SIZE * sizeof(GLfloat);
	glVertexAttribPointer(VERTEX_COLOR_INDX, VERTEX_COLOR_SIZE, GL_FLOAT, GL_FALSE, vtxStride, (const void*)offset);

	glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_SHORT, 0);

	glDisableVertexAttribArray(VERTEX_POS_INDX);
	glDisableVertexAttribArray(VERTEX_COLOR_INDX);

	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
}

void Draw(ESContext *esContext)
{
	UserData *userData = (UserData*)esContext->userData;

	//三个顶点  
	GLfloat vertices[3 * VERTEX_POS_SIZE + VERTEX_COLOR_SIZE] =
	{
		-0.5f, 0.5f, 0.0f, // v0
		1.0f, 0.0f, 0.0f, 1.0f, //c0
		-1.0f, -0.5f, 0.0f, //v1
		0.0f, 1.0f, 0.0f, 1.0f, //c1
		0.0f, -0.5f, 0.0f, //v2
		0.0f, 0.0f, 1.0f, 1.0f //c2
	};
	//索引缓冲数据
	GLushort indices[3] = { 0, 1, 2 };

	glViewport(0, 0, esContext->width, esContext->height);
	glClear(GL_COLOR_BUFFER_BIT);
	glUseProgram(userData->programObject);
	glUniform1f(userData->offsetLoc, 0.0f);

	DrawPrimitiveWithoutVBOs(vertices, sizeof(GLfloat) * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE), 3, indices);

	glUniform1f(userData->offsetLoc, 1.0f);

	DrawPrimitiveWithVBOs(esContext, 3, vertices, sizeof(GLfloat) * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE), 3, indices);
}