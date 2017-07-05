#ifndef OBJLOADER_H
#define OBJLOADER_H

#include <vector>
#include <string>
#include <glm/glm.hpp>

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <cstdlib>

class ObjLoader
{
private:
    std::string filename;
    std::vector<glm::vec4> vertices;
    std::vector<glm::vec3> normals;
    std::vector<GLushort> elements;
public:
    static ObjLoader cube;
    static ObjLoader monkey;
public:
    ObjLoader(const std::string filename);
    void load_obj();
    void draw();
    static void loadObjALL();
};

#endif // OBJLOADER_H
