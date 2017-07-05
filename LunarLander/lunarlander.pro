#-------------------------------------------------
#
# Project created by QtCreator 2015-02-09T17:26:14
#
#-------------------------------------------------

QT       += core

QT       -= gui

TARGET = lunarlander
CONFIG   += console
CONFIG   -= app_bundle

TEMPLATE = app


SOURCES += main.cpp \
    lander.cpp \
    freeflycamera.cpp \
    vector3d.cpp \
    pave.cpp \
    planet.cpp \
    corps.cpp \
    sdlglutils.cpp \
    particle.cpp \
    positionnable.cpp \
    texture.cpp \
    objloader.cpp \
    triplet.cpp \
    landercommandable.cpp \
    scene.cpp \
    ordinateur.cpp \
    gauss.cpp

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/release/ -lSDL
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/debug/ -lSDL
else:unix: LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/ -lSDL

INCLUDEPATH += $$PWD/../../../usr/lib/x86_64-linux-gnu
DEPENDPATH += $$PWD/../../../usr/lib/x86_64-linux-gnu

LIBS += -lGL
LIBS += -lGLU
LIBS += -lglut
LIBS += -lGLEW
LIBS += -lm

HEADERS += \
    lander.h \
    freeflycamera.h \
    vector3d.h \
    pave.h \
    planet.h \
    corps.h \
    sdlglutils.h \
    particle.h \
    positionnable.h \
    texture.h \
    objloader.h \
    triplet.h \
    landercommandable.h \
    scene.h \
    ordinateur.h \
    gauss.h

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/release/ -lSDL_image
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/debug/ -lSDL_image
else:unix: LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/ -lSDL_image

INCLUDEPATH += $$PWD/../../../usr/lib/x86_64-linux-gnu
DEPENDPATH += $$PWD/../../../usr/lib/x86_64-linux-gnu

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/release/ -lGLEW
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/debug/ -lGLEW
else:unix: LIBS += -L$$PWD/../../../usr/lib/x86_64-linux-gnu/ -lGLEW

INCLUDEPATH += $$PWD/../../../usr/lib/x86_64-linux-gnu
DEPENDPATH += $$PWD/../../../usr/lib/x86_64-linux-gnu

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../../../../../../../usr/lib/x86_64-linux-gnu/release/ -lSDL_ttf
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../../../../../../../usr/lib/x86_64-linux-gnu/debug/ -lSDL_ttf
else:unix: LIBS += -L$$PWD/../../../../../../../usr/lib/x86_64-linux-gnu/ -lSDL_ttf

INCLUDEPATH += $$PWD/../../../../../../../usr/lib/x86_64-linux-gnu
DEPENDPATH += $$PWD/../../../../../../../usr/lib/x86_64-linux-gnu
