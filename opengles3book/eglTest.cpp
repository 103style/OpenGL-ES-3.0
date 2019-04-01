#include "esUtil.h"

EGLBoolean initializeWindow(EGLNativeWindow nativeWindow) {
	const EGLint configAttribs[] =
	{
		EGL_RENDER_TYPE, EGL_WINDOW_BIT,
		EGL_RED_SIZE,8,
		EGL_GREEN_SIZE,8,
		EGL_BLUE_SIZE,8,
		EGL_DEPTH_SIZE,24,
		EGL_NONE
	};

	const EGLint contextAttribs[] =
	{
		EGL_CONTEXT_CLIENT_VERSION,3,
		EGL_NONE
	};

	EGLDisplay display = eglGetDisplay(EGL_DEFAULT_DISPLAY);
	if (display == EGL_NO_DISPLAY)
	{
		return EGL_FALSE;
	}

	EGLint major, minor;
	if (!eglInitialize(display, &major, &minor))
	{
		return EGL_FALSE;
	}

	EGLConfig config;
	EGLint numConfigs;
	if (!eglChooseConfig(display, configAttribs, &config, 1, &numConfigs))
	{
		return EGL_FALSE;
	}

	EGLSurface window = eglCreateWindowSurface(display, config, nativeWindow, NULL);
	if (window == EGL_NO_SURFACE)
	{
		return EGL_FALSE;
	}

	EGLContext context = eglCreateContext(display,config,EGL_NO_CONTEXT,contextAttribs);

	if (context == EGL_NO_CONTEXT)
	{
		return EGL_FALSE;
	}
	
	if (!eglMakeCurrent(display,window,window,context))
	{
		return EGL_FALSE;
	}

	return EGL_TRUE;
}
