#include"esUtil.h"

GLfloat *vtxMappedBuf;
GLushort *idxMappedBuf;

glGenBuffers(2, userData->vboIds);

glBindBuffer(GL_ARRAY_BUFFER, userData->vboIds[0]);
glBufferData(GL_ARRAY_BUFFER, vtxStride * numVertices, NULL, GL_STATIC_DRAW);

vtxMappedBuf = (GLfloat*)glMapBufferRange(GL_ARRAY_BUFFER, 0, vtxStride*numVertices, GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT);

if (vtxMappedBuf == NULL) {
	esLogMessage("Error mapping vertex buffer object");
	return;
}
//�������ݵ�ӳ�仺����
memcpy(vtxMappedBuf, vtxBuf, vtxStride * numVertics);

//ȡ�����黺��������ӳ��
if (glUnmapBuffer(GL_ARRAY_BUFFER) == GL_FALSE)
{
	esLogMessage("Error unmapping array buffer object.");
	return;
}

glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, userData->vboIds[1]);
glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLushort) * numIndices, NULL, GL_STATIC_DRAW);

idxMappedBuf = (GLushort*)glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER, 0, sizeof(GLushort) * numIndices, GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT);

if (idxMappedBuf == NULL)
{
	esLogMessage("Error mapping element buffer object.");
	return;
}

//�������ݵ�ӳ�仺����
memcpy(idxMappedBuf, indices, sizeof(GLushort) * numIdices);
//ȡ��Ԫ�����黺��������ӳ��
if (glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER) == GL_FALSE)
{
	esLogMessage("Error unmapping  element buffer object");
	return;
}