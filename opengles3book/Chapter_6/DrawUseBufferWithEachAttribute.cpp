#include "esUtil.h"

#define VERTEX_POS_SIZE  3 // x, y, and z
#define VERTEX_COLOR_SIZE  4 // r, g, b, and a

#define VERTEX_POS_INDX  0
#define VERTEX_COLOR_INDX  1

void DrawPrimitiveWithVBOs(ESContext *esContext, GLint numVertices, GLfloat **vtxBuf, GLint *vtxStrides, GLint numIndices, GLushort *indices)
{
	UserData *userData = (UserData*)esContext->userData;

	//vboId[0] : ���ڴ洢 ����λ��
	//vboId[1] : ���ڴ洢 ������ɫ
	//vboId[2] : ���ڴ洢 Ԫ������
	if (userData->vboIds[0] == 0 && userData->vboIds[1] == 0 && userData->vboIds[2] == 0)
	{
		//ֻ�ڵ�һ�λ���ʱ����
		glGenBuffers(3, userData->vboIds);

		glBindBuffer(GL_ARRAY_BUFFER, userData->vboIds[0]);
		glBufferData(GL_ARRAY_BUFFER, vtxStrides[0] * numIndices, vtxBuf[0], GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, userData->vboIds[1]);
		glBufferData(GL_ARRAY_BUFFER, vtxStrides[1] * numIndices, vtxBuf[1], GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, userData->vboIds[2]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLushort) * numIndices, indices, GL_STATIC_DRAW);
	}

	glBindBuffer(GL_ARRAY_BUFFER, userData->vboIds[0]);
	glEnableVertexAttribArray(VERTEX_POS_INDX);
	glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE, GL_FLOAT, GL_FALSE, vtxStrides[0], 0);

	glBindBuffer(GL_ARRAY_BUFFER, userData->vboIds[1]);
	glEnableVertexAttribArray(VERTEX_COLOR_INDX);
	glVertexAttribPointer(VERTEX_COLOR_INDX, VERTEX_COLOR_SIZE, GL_FLOAT, GL_FALSE, vtxStrides[1], 0);


	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, userData->vboIds[2]);

	glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_SHORT, 0);

	glDisableVertexAttribArray(VERTEX_POS_INDX);
	glDisableVertexAttribArray(VERTEX_COLOR_INDX);

	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
}