#include "objloader.h"

#include <fstream>
#include <iostream>
#include <sstream>

ObjLoader ObjLoader::cube("object/cube.obj");
ObjLoader ObjLoader::monkey("object/monkey.obj");

ObjLoader::ObjLoader(const std::string filename)
{
    this->filename = filename;
}

void ObjLoader::load_obj() {
    this->elements.clear();
    this->normals.clear();
    this->vertices.clear();

  std::ifstream in(filename.c_str(), std::ios::in);
  if (!in) { std::cerr << "Cannot open " << filename << std::endl; exit(1); }

  std::string line;
  while (getline(in, line)) {
    if (line.substr(0,2) == "v ") {
      std::istringstream s(line.substr(2));
      glm::vec4 v; s >> v.x; s >> v.y; s >> v.z; v.w = 1.0f;
      vertices.push_back(v);
    }  else if (line.substr(0,2) == "f ") {
      std::istringstream s(line.substr(2));
      GLushort a,b,c;
      s >> a; s >> b; s >> c;
      a--; b--; c--;
      elements.push_back(a); elements.push_back(b); elements.push_back(c);
    }
    else if (line[0] == '#') { /* ignoring this line */ }
    else { /* ignoring this line */ }
  }

  normals.resize(vertices.size(), glm::vec3(0.0, 0.0, 0.0));
  for (unsigned int i = 0; i < elements.size(); i+=3) {
    GLushort ia = elements[i];
    GLushort ib = elements[i+1];
    GLushort ic = elements[i+2];
    glm::vec3 normal = glm::normalize(glm::cross(
      glm::vec3(vertices[ib]) - glm::vec3(vertices[ia]),
      glm::vec3(vertices[ic]) - glm::vec3(vertices[ia])));
    normals[ia] = normals[ib] = normals[ic] = normal;
  }
}

void ObjLoader::draw() {
    /*glEnableVertexAttribArray(attribute_v_coord);

      //Describe our vertices array to OpenGL (it can't guess its format automatically)
    glBindBuffer(GL_ARRAY_BUFFER, vbo_mesh_vertices);
    glVertexAttribPointer(
                attribute_v_coord,  // attribute
                4,                  // number of elements per vertex, here (x,y,z,w)
                GL_FLOAT,           // the type of each element
                GL_FALSE,           // take our values as-is
                0,                  // no extra data between each position
                0                   // offset of first element
              );

      glBindBuffer(GL_ARRAY_BUFFER, vbo_mesh_normals);
      glVertexAttribPointer(
        attribute_v_normal, // attribute
        3,                  // number of elements per vertex, here (x,y,z)
        GL_FLOAT,           // the type of each element
        GL_FALSE,           // take our values as-is
        0,                  // no extra data between each position
        0                   // offset of first element
      );

      glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_mesh_elements);
    int size;  glGetBufferParameteriv(GL_ELEMENT_ARRAY_BUFFER, GL_BUFFER_SIZE, &size);

    glDrawElements(GL_TRIANGLES, size/sizeof(GLushort), GL_UNSIGNED_SHORT, 0);
    */
}

void ObjLoader::loadObjALL() {
    ObjLoader::cube.load_obj();
    ObjLoader::monkey.load_obj();
}
