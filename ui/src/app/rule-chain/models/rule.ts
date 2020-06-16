import {Node} from './node';
import {Connection} from './connection';

export interface Rule {
  id?: string;
  name: string;
  description?: string;
  nodeDocuments: Node[];
  nodeConnectionInfos: Connection[];
  started: boolean;
}
